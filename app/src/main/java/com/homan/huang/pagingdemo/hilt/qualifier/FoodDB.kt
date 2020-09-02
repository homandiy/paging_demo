package com.homan.huang.stockrestapi.dagger.qualifier

import java.lang.annotation.Documented
import javax.inject.Qualifier


@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class FoodDB(val value: DatabaseTypeEnum)

