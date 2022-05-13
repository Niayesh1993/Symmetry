package xyz.zohre.domain.exeption

import java.lang.Exception

class RemoteCallException(override val message: String?) : Exception(message)