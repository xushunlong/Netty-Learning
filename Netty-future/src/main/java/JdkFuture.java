import java.util.concurrent.*;

public class JdkFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService =Executors.newFixedThreadPool(1);
        Future<Integer> submit = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1000);
                return 1;
            }
        });
        Integer res = submit.get();
        System.out.println(res);
    }
}
