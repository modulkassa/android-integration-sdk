// IModulKassaOperationCallback.aidl
package ru.modulkassa.pos.integration.service;

/**
* Результат выполенния оперции на стороне МодульКассы
*/
interface IPluginServiceCallback {
    /**
     * Операция выполнилась успешно
     */
    void succeeded(in Bundle data);

    /**
    * Произошла ошибка во время выполнения
    */
    void failed(String message, in Bundle extraData);

    /**
    * Процесс оплаты был отменен пользователем
    */
    void cancelled();
}
