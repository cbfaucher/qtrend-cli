package com.quartz.qtrend.cli.model.dao

/**
 * Base exceptions for DAOs
 */
class DaoException(msg: String = "", nested: Throwable = null) extends RuntimeException(msg, nested)
