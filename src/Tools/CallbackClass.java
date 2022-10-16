package Tools;

import Object.Enums.GenderEnum;
import Service.LoginManager;

public interface CallbackClass {
    public void doCallback();
}

//    Function f=in->{
//        System.out.println("lambda test");
//    return true;};
//
//    public void te(Supplier fun){
//        fun;
//    }
//
//    public static void main(String[] args) {
//        LoginManager l=new LoginManager();
//        l.te(l.f);
//    }
//
//    Supplier f=()->{
//        System.out.println("lambda test");
//        return true;};

    //call back
//    public void loginSuccess() {
//        System.out.println(s);
//    }
//
//    public void t(){
//        Tenant tenant=new Tenant("","","","","", GenderEnum.MALE);
//        tenant.test(()->{
//            loginSuccess();
//            System.out.println("callback");
//        });
//    }
//
//    public static void main(String[] args) {
//        LoginManager loginManager=new LoginManager();
//        loginManager.t();
//    }
//    public void test(CallbackClass callback){
//        new Thread(() -> {
//            // 这里是业务逻辑处理
//            System.out.println("子线任务开始执行:" + Thread.currentThread().getId());
//            // 为了能看出效果 ，让当前线程阻塞5秒
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("子线任务结束执行:");
//            callback.doCallback();
//        }).start();
//        System.out.println("after");
//    }