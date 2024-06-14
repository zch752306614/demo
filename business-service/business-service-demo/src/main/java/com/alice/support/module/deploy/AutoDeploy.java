package com.alice.support.module.deploy;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Zhang Chenhuang
 * @date 2024/6/6 18:01:29
 */
public class AutoDeploy {

    public static void demo() {
        try {
            // 定义要执行的命令
            String command = "cmd.exe /c dir";

            // 创建 ProcessBuilder 对象并设置命令
            ProcessBuilder pb = new ProcessBuilder(command);

            // 将输出流和错误流合并
            pb.redirectErrorStream(true);

            // 启动进程
            Process process = pb.start();

            // 获取进程的输入流，用于读取命令执行结果
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // 读取命令执行结果
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 等待命令执行完成
            int exitCode = process.waitFor();
            System.out.println("Command executed with exit code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<String> serverNameList = new ArrayList<String>();
//        serverNameList.add("zuul");
        serverNameList.add("novel");
        deploy(serverNameList);
//        demo();
    }

    private static void deploy(List<String> serverNameList) {
        try {
            // 读取 YAML 配置文件
            List<Map<String, Object>> projects = readYamlConfig("E:\\Project\\Idea\\alice\\demo\\business-service\\business-service-demo\\src\\main\\resources\\deploy.yml");

            // 根据项目名过滤要部署的项目
            for (Map<String, Object> project : projects) {
                String projectName = (String) project.get("name");
                if (serverNameList.contains(projectName)) {
                    deployProject(project);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void deployProject(Map<String, Object> project) {
        String projectName = (String) project.get("name");
        String localJarPath = (String) project.get("localJarPath");
        String ftpServer = (String) project.get("ftpServer");
        Integer port = (Integer) project.get("ftpPort");
        String ftpUsername = (String) project.get("ftpUsername");
        String ftpPassword = (String) project.get("ftpPassword");
        String remoteRootPath = (String) project.get("remoteJarPath");
        String startupScript = (String) project.get("startupScript");
        Session session = null;
        try {
            // 执行 Maven 打包
//            executeMavenPackage();

            // 获取上传的文件名
            String uploadedFileName = getUploadedFileName(localJarPath);

            // 拼接远程路径
            String remoteJarPath = remoteRootPath + "/" + uploadedFileName;

            // 创建session连接
            JSch jsch = new JSch();
            session = jsch.getSession(ftpUsername, ftpServer, port);
            session.setPassword(ftpPassword);
            // 跳过主机密钥检查
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            // FTP 上传
            uploadFile(session, localJarPath, remoteJarPath);

            // 执行启动脚本
            executeUpdateScript(session, startupScript);

            System.out.println("Deployment of project " + projectName + " completed successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    private static String getUploadedFileName(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }

    private static void uploadFile(Session session, String localFilePath, String remoteFilePath) throws Exception {
        ChannelSftp channelSftp = null;

        try {
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            // 检查远程路径是否存在，如果不存在则创建
            createRemoteDirectory(channelSftp, remoteFilePath);

            File localFile = new File(localFilePath);
            InputStream inputStream = Files.newInputStream(localFile.toPath());

            System.out.println("Start uploading file " + localFilePath + " to " + remoteFilePath);
            channelSftp.put(inputStream, remoteFilePath);
            inputStream.close();
            System.out.println("File uploaded successfully.");
        } finally {
            // 断开连接
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
        }
    }

    private static void createRemoteDirectory(ChannelSftp channelSftp, String remoteFilePath) throws Exception {
        String[] directories = remoteFilePath.split("/");
        StringBuilder path = new StringBuilder("/");

        for (String directory : directories) {
            if (!directory.isEmpty() && !directory.contains(".")) {
                path.append(directory).append("/");
                try {
                    channelSftp.cd(path.toString());
                } catch (Exception e) {
                    channelSftp.mkdir(path.toString());
                    channelSftp.cd(path.toString());
                }
            }
        }
    }

    private static void executeMavenPackage() throws IOException {
        System.out.println("Executing Maven package command...");
        ProcessBuilder pb = new ProcessBuilder("E: && cd \\Project\\Idea\\alice\\demo", "mvn install");
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // 打印 Maven 打包输出信息
        InputStream inputStream = process.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            System.out.print(new String(buffer, 0, bytesRead));
        }

        // 等待 Maven 打包完成
        try {
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Maven package command executed successfully.");
            } else {
                System.out.println("Failed to execute Maven package command. Exit code: " + exitCode);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private static void executeUpdateScript(Session session, String scriptPath) throws Exception {
        ChannelExec channelExec = null;
        try {
            channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand("sh " + scriptPath);
            channelExec.setErrStream(System.err);
            channelExec.setOutputStream(System.out);
            channelExec.connect();

            int exitStatus = channelExec.getExitStatus();
            if (exitStatus == 0) {
                System.out.println("Update script executed successfully.");
            } else {
                System.out.println("Update script execution failed with exit status: " + exitStatus);
            }
        } finally {
            if (channelExec != null && channelExec.isConnected()) {
                channelExec.disconnect();
            }
        }
    }

    private static List<Map<String, Object>> readYamlConfig(String filePath) throws IOException {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            return yaml.load(inputStream);
        }
    }

}
