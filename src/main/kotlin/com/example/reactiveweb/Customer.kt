package com.example.reactiveweb

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
@Document
data class Customer(
		@Id val id: String? = null,
		val email: String? = null
)