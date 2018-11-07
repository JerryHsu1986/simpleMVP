package com.haoche51.mvp.rx;

import com.haoche51.mvp.entity.ResponseEntity;
import com.haoche51.mvp.utils.JsonUtil;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author HaiboXu on 2018/10/17
 * @github https://github.com/JerryHsu1986
 */
public class ObserverMapper {
    /**
     * 得到返回结果
     * @param response
     * @param <T>
     * @return
     */
    public static <T> T responseData(ResponseEntity<T> response){
        return response.getData();
    }

    /**
     * 统一线程处理， //compose 简化线程处理
     * @param <T>
     * @return
     */
    public static <T>FlowableTransformer<T,T> rxSchedulerHelper(){
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一网络返回结果
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<ResponseEntity<T>,T> transformer(){
        return new FlowableTransformer<ResponseEntity<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<ResponseEntity<T>> upstream) {
                return upstream.flatMap(new Function<ResponseEntity<T>, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(ResponseEntity<T> responseEntity) throws Exception {
                        if (responseEntity.getErrno() != 0){
                            return Flowable.error(new Exception(responseEntity.getErrmsg()));
                        }else {
                            return createData(responseEntity.getData());
                        }
                    }
                });
            }
        };
    }

    /**
     * 生成flowable
     */

    public static <T> Flowable<T> createData(final T data){

        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                emitter.onNext(data);
                emitter.onComplete();

            }
        }, BackpressureStrategy.BUFFER);
    }

}
