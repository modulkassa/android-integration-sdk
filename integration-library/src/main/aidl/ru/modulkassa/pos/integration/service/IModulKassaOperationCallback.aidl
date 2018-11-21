// IModulKassaOperationCallback.aidl
package ru.modulkassa.pos.integration.service;

/**
* Результат выполенния оперции на стороне МодульКассы
*/
interface IModulKassaOperationCallback {
    /**
     * Операция выполнилась успешно
     */
    void succeed(String data);

    /**
    * Произошла ошибка во время выполнения
    */
    void failed(String message, String extraData);
}
