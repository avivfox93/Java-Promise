package Promise;

import java.util.ArrayList;

public class Promise<R> implements PromiseInterface{

    private final ArrayList<PromiseThenCallback<R>> thenCallbacks;
    private final ArrayList<PromiseCatchCallback> catchCallbacks;
    private final ArrayList<PromiseAlwaysCallback> alwaysCallbacks;
    private final ArrayList<PromiseCancelledCallback> cancelledCallbacks;
    private PromiseFunction<R> func;
    private Thread thread = new Thread();

    /**
     *
     * @param func - function to run inside thread
     */
    public Promise(PromiseFunction<R> func){
        this.thenCallbacks = new ArrayList<>();
        this.catchCallbacks = new ArrayList<>();
        this.alwaysCallbacks = new ArrayList<>();
        this.cancelledCallbacks = new ArrayList<>();
        this.func = func;
    }

    /**
     *
     * @param callback - run if promise function ran ok
     * @return returns the promise
     */
    @Override
    public Promise<R> then(PromiseThenCallback callback){
        this.thenCallbacks.add(callback);
        return this;
    }

    /**
     *
     * @param callback - run when promise function finish running (even if cancelled)
     * @return return the promise
     */
    @Override
    public Promise<R> always(PromiseAlwaysCallback callback){
        alwaysCallbacks.add(callback);
        return this;
    }

    /**
     *
     * @param callback - run when promise function throws PromiseCatchException
     * @return return the promise
     */
    @Override
    public Promise<R> not(PromiseCatchCallback callback){
        this.catchCallbacks.add(callback);
        return this;
    }

    /**
     *
     * @param callback - run when promise function get cancelled
     * @return return the promise
     */
    @Override
    public Promise<R> cancelled(PromiseCancelledCallback callback){
        this.cancelledCallbacks.add(callback);
        return this;
    }

    /**
     * cancel the promise if running and call the cancelled callbacks
     */
    @Override
    public void cancel(){
        this.thread.interrupt();
    }

    /**
     * run the promise
     */
    @Override
    public void run() {
        this.thread = new Thread(() -> {
            try {
                R result = func.run();
                if (result == null) throw new PromiseCatchException(0);
                for (PromiseThenCallback<R> c : thenCallbacks) c.thenFunc(result);
            } catch (PromiseCatchException e) {
                for (PromiseCatchCallback c : catchCallbacks) c.catchFunc(e);
            } catch (InterruptedException e) {
                for (PromiseCancelledCallback c : cancelledCallbacks) c.cancelled();
            }finally {
                for(PromiseAlwaysCallback c : alwaysCallbacks) c.always();
            }
        });
        thread.start();
    }
}