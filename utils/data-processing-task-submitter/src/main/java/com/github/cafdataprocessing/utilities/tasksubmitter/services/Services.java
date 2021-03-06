/*
 * Copyright 2015-2017 Hewlett Packard Enterprise Development LP.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.cafdataprocessing.utilities.tasksubmitter.services;

import com.github.cafdataprocessing.utilities.queuehelper.RabbitProperties;
import com.github.cafdataprocessing.utilities.queuehelper.RabbitServices;
import com.hpe.caf.api.Codec;
import com.hpe.caf.api.worker.ManagedDataStore;
import com.hpe.caf.codec.JsonCodec;
import com.github.cafdataprocessing.utilities.tasksubmitter.properties.StorageProperties;
import com.github.cafdataprocessing.utilities.tasksubmitter.properties.TaskSubmitterProperties;
import com.hpe.caf.worker.datastore.fs.FileSystemDataStoreConfiguration;
import com.hpe.caf.worker.queue.rabbit.RabbitWorkerQueueConfiguration;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Provides access to commonly used components across application.
 */
public class Services {
    private static Services instance;
    private static RabbitServices rabbitServicesInstance = RabbitServices.getInstance();
    private static FileSystemStorageServices storageServices = FileSystemStorageServices.getInstance();

    private final TaskSubmitterProperties taskSubmitterProperties;

    public static Services getInstance() {
        if (instance == null) {
            instance = new Services();
        }
        return instance;
    }

    private Services() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.registerBeanDefinition("TaskSubmitterProperties", new RootBeanDefinition(TaskSubmitterProperties.class));
        context.refresh();
        taskSubmitterProperties = context.getBean(TaskSubmitterProperties.class);
    }

    public Codec getCodec(){
        return new JsonCodec();
    }

    public RabbitProperties getRabbitProperties(){
        return rabbitServicesInstance.getRabbitProperties();
    }

    public RabbitWorkerQueueConfiguration getRabbitConfiguration(){
        return rabbitServicesInstance.getRabbitQueueConfiguration();
    }

    public ManagedDataStore getStorageDataStore(){
        return storageServices.getDataStore();
    }

    public FileSystemDataStoreConfiguration getStorageConfiguration(){
        return storageServices.getStorageConfiguration();
    }

    public StorageProperties getStorageProperties(){
        return storageServices.getStorageProperties();
    }

    public TaskSubmitterProperties getTaskSubmitterProperties(){
        return taskSubmitterProperties;
    }
}
