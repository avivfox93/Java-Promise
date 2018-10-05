package Promise;

import java.util.Date;

public class Test {
    public static void main(String[] arg){
        long startTime = new Date().getTime();
        PromiseAlwaysCallback finish = () -> {
            System.out.println((new Date().getTime() - startTime) + "ms");
        };
        Promise<String> promise = new Promise<String>(()->{
            for(int i = 0 ; i < 5 ; i++){
                Thread.sleep(1000);
            }
            return "finished";
        }).then(System.out::println)
                .not(err -> System.out.println(err.getMessage()))
                .cancelled(()->System.out.println("cancelled"))
                .always(finish);
        promise.run();
        System.out.println("start thread");
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Send cancel");
                promise.cancel();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }).start();
        new Promise<Long>(()-> new Date().getTime())
                .then(result->System.out.println(new Date().getTime() - (Long)result)).run();
    }
}
