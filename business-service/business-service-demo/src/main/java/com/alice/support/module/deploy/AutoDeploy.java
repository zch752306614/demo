package com.alice.support.module.deploy;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Zhang Chenhuang
 * @date 2024/6/6 18:01:29
 */
public class AutoDeploy {

    public static void demo() {
        try {
            // 定义要执行的命令
            String command = "cmd.exe /c dir"; // 示例命令：列出当前目录下的文件和子目录

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
//        deploy(serverNameList);
        demo();
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
        String ftpUsername = (String) project.get("ftpUsername");
        String ftpPassword = (String) project.get("ftpPassword");
        String remoteRootPath = (String) project.get("remoteRootPath");
        String startupScript = (String) project.get("startupScript");

        try {
            // 执行 Maven 打包
//            executeMavenPackage();

            // 获取上传的文件名
            String uploadedFileName = getUploadedFileName(localJarPath);

            // 拼接远程路径
            String remoteJarPath = remoteRootPath + "/" + uploadedFileName;

            // FTP 上传
            uploadFile(ftpServer, ftpUsername, ftpPassword, localJarPath, remoteJarPath);

            // 执行启动脚本
            executeStartupScript(startupScript);

            System.out.println("Deployment of project " + projectName + " completed successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String getUploadedFileName(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }

    private static void uploadFile(String server, String username, String password, String localFilePath,
                                   String remoteFilePath) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server);
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 检查远程路径是否存在，如果不存在则创建
            createRemoteDirectory(ftpClient, remoteFilePath);

            File localFile = new File(localFilePath);
            InputStream inputStream = new FileInputStream(localFile);

            System.out.println("Start uploading file " + localFilePath + " to " + remoteFilePath);
            boolean done = ftpClient.storeFile(remoteFilePath, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("File uploaded successfully.");
            } else {
                System.out.println("Failed to upload file.");
            }
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        }
    }

    private static void createRemoteDirectory(FTPClient ftpClient, String remoteFilePath) throws IOException {
        String[] directories = remoteFilePath.split("/");
        for (String directory : directories) {
            if (!directory.isEmpty()) {
                if (!ftpClient.changeWorkingDirectory(directory)) {
                    ftpClient.makeDirectory(directory);
                    ftpClient.changeWorkingDirectory(directory);
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

    private static void executeStartupScript(String scriptPath) throws IOException {
        System.out.println("Executing startup script: " + scriptPath);
        ProcessBuilder pb = new ProcessBuilder(scriptPath);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // 打印启动脚本输出信息
        InputStream inputStream = process.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            System.out.print(new String(buffer, 0, bytesRead));
        }

        // 等待启动脚本执行完成
        try {
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Startup script executed successfully.");
            } else {
                System.out.println("Failed to execute startup script. Exit code: " + exitCode);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private static List<Map<String, Object>> readYamlConfig(String filePath) throws IOException {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            return yaml.load(inputStream);
        }
    }

}
