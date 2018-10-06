package Promise;

public interface PromiseInterface<T> {
    PromiseInterface then(PromiseThenCallback<T> callback);
    PromiseInterface not(PromiseCatchCallback callback);
    PromiseInterface always(PromiseAlwaysCallback callback);
    PromiseInterface cancelled(PromiseCancelledCallback callback);
    void run();
    void runAndWait();
    void cancel();
}
interface PromiseThenCallback<T>{
    /**
     *
     * @param result - Generic object return'd from promise function
     */
    void thenFunc(T result);
}
interface PromiseCatchCallback{
    /**
     *
     * @param err - PromiseCatchException thrown by promise function
     */
    void catchFunc(PromiseCatchException err);
}
interface PromiseAlwaysCallback{
    void always();
}
interface PromiseCancelledCallback{
    void cancelled();
}
interface PromiseFunction<T>{
    /**
     *
     * @return - return the result of the function
     * @throws InterruptedException
     * @throws PromiseCatchException
     */
    T run() throws InterruptedException,PromiseCatchException;
}
class PromiseCatchException extends Throwable{
    private int status = 0;
    private Object data = null;
    PromiseCatchException(String msg, int status, Object data){
        super(msg);
        this.data = data;
        this.status = status;
    }
    PromiseCatchException(String msg, int status){
        super(msg);
        this.status = status;
    }
    PromiseCatchException(int status, Object data){
        super();
        this.status = status;
        this.data = data;
    }
    PromiseCatchException(int status){
        super();
        this.status = status;
    }
    public int getStatus() {
        return this.status;
    }
    public Object getData(){
        return this.data;
    }
}
