package com.example.retry;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.stereotype.Component;

@Component("retryLogger")
public class RetryLoggingListener implements RetryListener {
    @Override
    public <T, E extends Throwable> void onError(
            RetryContext context,
            RetryCallback<T, E> callback,
            Throwable throwable
    ) {
        System.out.println("Retry attempt #" + context.getRetryCount() +
                " due to: " + throwable.getMessage());
    }
}
