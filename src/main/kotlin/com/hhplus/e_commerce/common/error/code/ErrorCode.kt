package com.hhplus.e_commerce.common.error.code

import org.springframework.http.HttpStatus

sealed interface ErrorCode {

    val httpStatus: HttpStatus
    val errorCode: String
    val message: String

    enum class Common(
        override val httpStatus: HttpStatus,
        override val errorCode: String,
        override val message: String,
    ) : ErrorCode {
        NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON001", "찾을 수 없습니다."),
        BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON002", "잘못된 요청입니다."),
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON003", "서버에 문제가 발생했습니다."),
        LOCK_ACQUISITION_FAILED(HttpStatus.CONFLICT, "COMMON004", "락 획득에 실패했습니다."),
    }

    enum class User(
        override val httpStatus: HttpStatus,
        override val errorCode: String,
        override val message: String,
    ) : ErrorCode {
        NOT_FOUND_USER(HttpStatus.NOT_FOUND, "USER001", "유저를 찾을 수 없습니다."),
        INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, "USER002", "잔액이 부족합니다."),
    }

    enum class Balance(
        override val httpStatus: HttpStatus,
        override val errorCode: String,
        override val message: String,
    ) : ErrorCode {
        BAD_REQUEST_BALANCE(HttpStatus.BAD_REQUEST, "BALANCE001", "잘못된 잔액 충전 요청입니다."),
    }

    enum class Product(
        override val httpStatus: HttpStatus,
        override val errorCode: String,
        override val message: String,
    ) : ErrorCode {
        NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "PRODUCT001", "상품을 찾을 수 없습니다."),
        OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "PRODUCT002", "재고가 부족합니다."),
    }

    enum class Order(
        override val httpStatus: HttpStatus,
        override val errorCode: String,
        override val message: String,
    ) : ErrorCode {
        NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "ORDER001", "주문 이력을 찾을 수 없습니다."),
        INVALID_ORDER_STATUS(HttpStatus.BAD_REQUEST, "ORDER002", "주문 완료된 주문 번호가 아닙니다."),
        LOCK_ACQUISITION_FAILED(HttpStatus.BAD_REQUEST, "ORDER003", "결제 진행 중인 상품 입니다."),
    }

    enum class Carts(
        override val httpStatus: HttpStatus,
        override val errorCode: String,
        override val message: String,
    ) : ErrorCode {
        NOT_FOUND_CART(HttpStatus.NOT_FOUND, "CARTS001", "장바구니 상품 정보를 찾을 수 없습니다."),
    }

    enum class OutboxMessage(
        override val httpStatus: HttpStatus,
        override val errorCode: String,
        override val message: String,
    ) : ErrorCode {
        NOT_FOUND_OUTBOX_MESSAGE(HttpStatus.NOT_FOUND, "OUTBOX001", "아웃 박스 메세지가 없습니다."),
    }

}