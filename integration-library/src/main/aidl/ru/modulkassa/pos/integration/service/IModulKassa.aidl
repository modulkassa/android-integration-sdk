// IModulKassaInterface.aidl
package ru.modulkassa.pos.integration.service;

import ru.modulkassa.pos.integration.service.IModulKassaOperationCallback;

interface IModulKassa {
    /**
     * Передача МодульКассе какой-либо операции для выполнения
     */
    void executeOperation(String operationName, String jsonData, IModulKassaOperationCallback callback);
}
