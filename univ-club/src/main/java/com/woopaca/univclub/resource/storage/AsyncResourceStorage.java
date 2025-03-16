package com.woopaca.univclub.resource.storage;

import org.springframework.boot.task.SimpleAsyncTaskExecutorBuilder;
import org.springframework.core.task.TaskExecutor;

/**
 * 비동기로 이미지를 삭제하는 ResourceStorage 구현체
 */
public abstract class AsyncResourceStorage implements ResourceStorage {

    private final TaskExecutor taskExecutor;

    protected AsyncResourceStorage() {
        this.taskExecutor = new SimpleAsyncTaskExecutorBuilder()
                .concurrencyLimit(10)
                .threadNamePrefix("ResourceStorageTask-")
                .build();
    }

    @Override
    public final void delete(ResourceIdentifier identifier) {
        taskExecutor.execute(() -> {
            deleteImage(identifier);
        });
    }

    protected abstract void deleteImage(ResourceIdentifier identifier);
}
