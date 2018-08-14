/*
 * The MIT License
 * Copyright © 2018
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.bonitoo.influxdb.reactive.impl;

import javax.annotation.Nonnull;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * @author Jakub Bednar (bednar@github) (01/06/2018 11:56)
 * @since 1.0.0
 */
interface InfluxDBServiceReactive {

    String U = "u";
    String P = "p";
    String Q = "q";
    String DB = "db";
    String RP = "rp";
    String PARAMS = "params";
    String PRECISION = "precision";
    String CONSISTENCY = "consistency";
    String EPOCH = "epoch";
    String CHUNK_SIZE = "chunk_size";

    @POST("/write")
    @Nonnull
    Completable writePoints(@Query(U) String username,
                            @Query(P) String password,
                            @Query(DB) String database,
                            @Query(RP) String retentionPolicy,
                            @Query(PRECISION) String precision,
                            @Query(CONSISTENCY) String consistency,
                            @Body RequestBody points);

    @Streaming
    @GET("/query?chunked=true")
    @Nonnull
    Observable<ResponseBody> query(@Query(U) String username,
                                   @Query(P) String password,
                                   @Query(DB) String db,
                                   @Query(EPOCH) String epoch,
                                   @Query(CHUNK_SIZE) int chunkSize,
                                   @Query(value = Q, encoded = true) String query,
                                   @Query(value = PARAMS, encoded = true) String params);

    @GET("/ping")
    Maybe<Response<ResponseBody>> ping();
}
