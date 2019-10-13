package ua.serstarstory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Manager {
        private String startScript,projectName,whitelistRejectString,binaryName,env="STD";
        private boolean copyBinaries;
        private HashMap<String,Object> launcher=new HashMap<>();
        private HashMap<String,Object> certificate=new HashMap<>();
        private List<String> mirrors =new ArrayList<>();
        private List<HashMap<String,Object>> auth=new ArrayList<>(10);
        private HashMap<String,Object> protectHandler=new HashMap<>();
        private HashMap<String,Object> permissionsHandler=new HashMap<>();
        private HashMap<String,Object> hwidHandler=new HashMap<>();
        private HashMap<String,Object> components=new HashMap<>();
        private HashMap<String,Object> netty=new HashMap<>();
        private HashMap<String,Object> launch4j=new HashMap<>();
        public void addAuth(String type,HashMap<String,Object> map){
                auth.get(0).put(type,map);
        }
        public void setLauncher(HashMap<String, Object> launch) {
                launcher = launch;
        }

        public Manager(){
                mirrors.add("https://mirror.gravit.pro/");
                auth.add(new HashMap<>());
                auth.get(0).put("displayName","Default");
                auth.get(0).put("name","std");
                auth.get(0).put("isDefault",true);
                protectHandler.put("type","std");
                permissionsHandler.put("type","json");
                permissionsHandler.put("filename","permissions.json");
                hwidHandler.put("type","accept");
                setComponents();
        }
        public void setNetty(){
                netty.put("fileServerEnabled",true);
                netty.put("sendExceptionEnabled",true);
                netty.put("ipForwarding",false);
                netty.put("showHiddenFiles",false);
                netty.put("launcherURL","http://"+LSController.ip+":9274/Launcher.jar");
                netty.put("downloadURL","http://"+LSController.ip+":9274/%dirname%/");
                netty.put("launcherEXEURL","http://"+LSController.ip+":9274/Launcher.exe");
                netty.put("address","ws://"+LSController.ip+":9274/api");
                netty.put("bindings",new String[]{});
                HashMap<String,Object> performance=new HashMap<>();
                performance.put("usingEpoll",false);
                performance.put("bossThread",2);
                performance.put("workerThread",8);
                netty.put("performance",performance);
                List<HashMap<String,Object>> binds=new ArrayList<>();
                HashMap<String,Object> bind=new HashMap<>();
                bind.put("address","0.0.0.0");
                bind.put("port",9274);
                binds.add(bind);
                netty.put("binds",binds);
                netty.put("logLevel","DEBUG");

        }
        public void setL4J(boolean enable){
                launch4j.put("enable",enable);
                launch4j.put("alternative","no");
                launch4j.put("setMaxVersion",false);
                launch4j.put("maxVersion","1.8.999");
                launch4j.put("productName",projectName);
                launch4j.put("productVer","1.0");
                launch4j.put("fileDesc",projectName+" Launcher");
                launch4j.put("fileVer","1.0");
                launch4j.put("internalName","Launcher");
                launch4j.put("copyright","© "+projectName);
                launch4j.put("trademarks","This product is licensed under GPLv3");
                launch4j.put("txtFileVersion","%s, build %d");
                launch4j.put("txtProductVersion","%s, build %d");


        }
        private void setComponents(){
                HashMap<String,Object> a=new HashMap<>();
                HashMap<String,Object> b=new HashMap<>();
                a.put("message","Превышен лимит регистраций");
                a.put("excludeIps",new String[]{});
                a.put("rateLimit",3);
                a.put("rateLimitMillis",36000000);
                a.put("exclude",new String[]{});
                a.put("type","regLimiter");
                b.put("message","Превышен лимит авторизаций");
                b.put("rateLimit",3);
                b.put("rateLimitMillis",8000);
                b.put("exclude",new String[]{});
                b.put("type","authLimiter");
                components.put("regLimiter",a);
                components.put("authLimiter",b);
        }
        public void setCertificate(boolean a) {
                certificate.put("enabled",a);
        }

        public void setStartScript(String startScript) {
                this.startScript = startScript;
        }

        public void setProjectName(String projectName) {
                this.projectName = projectName;
        }

        public void setBinaryName(String binaryName) {
                this.binaryName = binaryName;
        }

        public void setCopyBinaries(boolean copyBinaries) {
                this.copyBinaries = copyBinaries;
        }

        public void setWhitelistRejectString(String whitelistRejectString) {
                this.whitelistRejectString = whitelistRejectString;
        }
}
