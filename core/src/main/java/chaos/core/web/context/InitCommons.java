package chaos.core.web.context;

import chaos.core.web.api.ApiHelper;
import chaos.core.web.db.DbHelper;
import chaos.core.web.event.EventHelper;
import chaos.core.web.exception.ExceptionHelper;
import chaos.core.web.kaptcha.KaptchaHelper;
import chaos.core.web.log.LogsHelper;
import chaos.core.web.region.RegionHelper;
import chaos.core.web.sms.SmsHelper;
import chaos.core.web.upfile.UpFileHelper;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class InitCommons implements ApplicationListener<ContextRefreshedEvent> {


//    @Value("${file.upload}")
//    @Value("${mail.send}")
//    private boolean email_send;

    private static final Logger log = Logger.getLogger(InitCommons.class);
    public ApplicationContext appContext;

    //  private static Scanner sc = new Scanner(System.in);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        appContext = event.getApplicationContext();

        log.info("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝Commons-web初始化＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
        ApiHelper.getInstance().init(appContext);
        UpFileHelper.initFielDir(appContext);
        DbHelper.init(appContext);
        CommonRequestHelper.init(appContext);
//        CommonSessionHelper.initFielDir(appContext);
        ExceptionHelper.init(appContext);
        LogsHelper.init(appContext);
        EventHelper.init(appContext);
        SmsHelper.init(appContext);
        KaptchaHelper.init(appContext);
        RegionHelper.init(appContext);
//        Tomcat tomcat = new Tomcat();
//        tomcat.addContext("data",Properties.file.getString(CommonKey.Propertie.file.upload_temp_ads));
//        try {
//            tomcat.start();
//        } catch (LifecycleException e) {
//            e.printStackTrace();
//        }
        log.info("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝Commons-web初始化完成＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
//        main(new String[]{});

//        Scanner sc = new Scanner(System.in);
//        System.out.println(sc.next());
    }




    public static void main(String[] args) {

        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
//        threadGroup.getParent().
        Thread thread = new Thread(new Runnable() {
            public void run() {
//                BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
                Scanner sc = new Scanner(System.in);

                while (true) {
                    if (sc.hasNext()) {

                        String con = sc.next();
                        System.out.println(con);
                    }else {
//                        try {
//                            Thread.sleep(5000);
//                        } catch (InterruptedException ignored) {}
                    }
//                    sc.
//                    System.out.println(sc.next() + "");
//                    System.out.println("1");
//                    try {
//                        if (strin.readLine() == null) continue;
//                        System.out.println(strin.readLine());
//                    } catch (IOException ignored) {
//                    }
                }
            }
        });
//        thread.setDaemon(true);
        thread.setName("kzt");
        thread.start();


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("adsfasf");
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000, 3000);


//        long l = 0;
//        try {
//            l = org.apache.commons.io.FileSystemUtils.freeSpaceKb("/");
//            System.out.println("InitProject.main" + l);
//            l = org.apache.commons.io.FileSystemUtils.freeSpaceKb("C:");
//            System.out.println("InitProject.main" + l);
//            l = org.apache.commons.io.FileSystemUtils.freeSpaceKb("D:");
//            System.out.println("InitProject.main" + l);
//            l = org.apache.commons.io.FileSystemUtils.freeSpaceKb("F:");
//            System.out.println("InitProject.main" + l);
//            l = org.apache.commons.io.FileSystemUtils.freeSpaceKb("G:");
//            System.out.println("InitProject.main" + l);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        for (java.io.File f : java.io.File.listRoots()) {
//
////            System.out.println(fileSys.getSystemDisplayName(f));//获取系统卷标及名字
////
////            System.out.println(fileSys.getSystemTypeDescription(f));//获取系统卷的类型
//            System.out.println(f.getPath());
//            System.out.println(f.getTotalSpace());//获取该卷大小(单位：字节)
//
//            System.out.println(f.getFreeSpace());//获取该卷可用大小(单位：字节)
//
//        }
    }
}