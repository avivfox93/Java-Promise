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
    })
    .then((result)->{ // Run if promise returns result. T result
       do somthing with result... 
    })
    .not((error)-> { // Run if promise throws PromiseCatchException PromiseCatchException error
        do somthing with error... // message, statusCode, data
    })
    .cancelled(()->{ // When promise.cancel() called. no arguments
        do somthing...
    })
    .always(()->{ // Always run after job as finished. no arguments
        do somthing...
    });
    promise.run(); // run the promise
    ```
    
    You can add as many .then(), .not(), .always(), .cancelled as you like.
    You can also use .runAndWait() wich waits for the promise to finish.