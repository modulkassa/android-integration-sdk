// IPluginService.aidl
package ru.modulkassa.pos.integration.service;

import ru.modulkassa.pos.integration.service.IPluginServiceCallback;

interface IPluginService {
    /**
     * Передача расширению кассы какой-либо операции для выполнения
     */
    void executeOperation(String operationName, in Bundle data, IPluginServiceCallback callback);
}
