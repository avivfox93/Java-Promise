# Java-Promise

Usage
-
- With lambda:
    ```java
    Promise<T> promise = new Promise<T> (()->{
        do somthing...
        if(ok)
            return result; // T
        else
            throw new PromiseCatchException(errMessage, statusCode, data); //String, int, Object
    });
    promise.then((result)->{ // T result
       do somthing with result... 
    });
    promise.not((error)-> { // PromiseCatchException error
        do somthing with error... // message, statusCode, data
    });
    promise.cancelled(()->{ // When promise.cancel() called. no arguments
        do somthing...
    });
    promise.always(()->{ // Always run after job as finished. no arguments
        do somthing...
    });
    promise.run(); // run the promise
    ```