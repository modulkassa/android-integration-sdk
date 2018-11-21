package ru.modulkassa.pos.integration.entity

import android.os.Bundle

/**
 * Сущность поддерживает сохранение в Bundle
 */
interface Bundable {
    fun toBundle(): Bundle
}