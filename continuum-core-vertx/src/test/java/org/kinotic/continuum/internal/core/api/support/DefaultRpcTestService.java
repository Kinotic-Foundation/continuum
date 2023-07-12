/*
 *
 * Copyright 2008-2021 Kinotic and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kinotic.continuum.internal.core.api.support;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.kinotic.continuum.api.security.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 *
 * Created by navid on 10/30/19
 */
@Component
public class DefaultRpcTestService implements RpcTestService{

    @Autowired
    private Vertx vertx;

    @Override
    public String getString() {
        return RpcTestService.STRING_VALUE;
    }

    @Override
    public SimpleObject getSimpleObject() {
        return STATIC_SIMPLE_OBJECT;
    }

    @Override
    public String getSimpleObjectToString(SimpleObject simpleObject) {
        return simpleObject.toString();
    }

    @Override
    public String getUnknownFailure() {
        throw new UnknownThrowable("Everything failed Sucka!");
    }

    @Override
    public String getAnotherString() {
        return RpcTestService.STRING_VALUE;
    }

    @Override
    public Future<String> getVertxFutureNullString() {
        return Future.succeededFuture(null);
    }

    @Override
    public ABunchOfArgumentsHolder acceptABunchOfArguments(int intValue,
                                            long longValue,
                                            String stringValue,
                                            boolean boolValue,
                                            SimpleObject simpleObject,
                                            List<String> listOfStrings) {
        return new ABunchOfArgumentsHolder(intValue, longValue, stringValue, boolValue, simpleObject, listOfStrings);
    }

    @Override
    public List<List<String>> getAListOfLists(List<List<String>> inputList) {
        return inputList.stream().map(strings -> strings.stream().map(s -> "Hello "+ s).collect(Collectors.toList())).collect(Collectors.toList());
    }

    @Override
    public List<String> getListOfStrings() {
        return LIST_OF_STRINGS;
    }

    @Override
    public Integer putListOfStrings(List<String> strings) {
        return strings.size();
    }

    @Override
    public List<String> modifyListOfStrings(String[] stringsToModify) {
        return Arrays.stream(stringsToModify).map(s -> "Hello "+ s).collect(Collectors.toList());
    }

    @Override
    public Flux<Integer> getLimitedFlux() {
        return Flux.just(1,2,3,4,5);
    }

    @Override
    public Flux<String> getInfiniteFlux() {
        return Flux.create(sink -> {
            AtomicLong count = new AtomicLong(0);
            long timerId = vertx.setPeriodic(1000, l ->{
                sink.next("Hello Sucka " + count.get());
                count.incrementAndGet();
            });

            sink.onDispose(() ->  {
                vertx.cancelTimer(timerId);
            });

        });
    }

    @Override
    public Mono<String> getMonoWithValue() {
        return Mono.just("Hello Bob");
    }

    @Override
    public Mono<Void> getMonoWithVoidFromEmpty() {
        return Mono.create(MonoSink::success);
    }

    @Override
    public Mono<Void> getMonoWithVoidFromNull() {
        return Mono.create(sink -> {
            sink.success(null);
        });
    }

    @Override
    public Mono<String> getMonoStringNull() {
        return Mono.create(sink -> {
            sink.success(null);
        });
    }

    @Override
    public Mono<Integer> getMonoIntegerNull() {
        return Mono.create(sink -> {
            sink.success(null);
        });
    }

    @Override
    public Mono<String> getMonoFailure() {
        return Mono.error(new IllegalStateException("Something went terribly wrong"));
    }

    @Override
    public Mono<String> getMonoEmptyString() {
        return Mono.just("");
    }

    @Override
    public Mono<String> getMonoStringLiterallyNull() {
        return Mono.just("null");
    }

    @Override
    public Mono<String> firstArgParticipant(Participant participant, String suffix){
        return Mono.just(participant.getId() + suffix);
    }

    @Override
    public Mono<String> middleArgParticipant(String prefix, Participant participant, String suffix){
        return Mono.just(prefix + participant.getId() + suffix);
    }

    @Override
    public Mono<String> lastArgParticipant(String prefix, Participant participant){
        return Mono.just(prefix + participant.getId());
    }

    private static class UnknownThrowable extends RuntimeException{
        public UnknownThrowable(String message) {
            super(message);
        }
    }
}
