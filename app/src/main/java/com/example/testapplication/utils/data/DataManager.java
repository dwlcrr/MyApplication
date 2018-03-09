package com.example.testapplication.utils.data;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * 数据管理类
 */
public abstract class DataManager<T> {


    private T data;

    private PublishSubject<T> publishSubject;
    private BehaviorSubject<T> behaviorSubject;

    public DataManager() {
        init();
    }

    private void init() {
        publishSubject = PublishSubject.create();
        behaviorSubject = BehaviorSubject.create();
    }

    public Observable<T> rx() {
        return publishSubject.filter(data -> data != null);
    }

    public Observable<T> rxBehavior() {
        return behaviorSubject.filter(data -> data != null);
    }

    /**
     * 实际应用的时候，data一般是对象，初始化为null，get的返回值需要判断，如果是null，可以{@link #refresh()}.
     *
     * @return
     */
    public T get() {
        return data;
    }

    /**
     * 手动更新数据
     *
     * @param data 新数据
     */
    public void set(T data) {
        this.data = data;
        doOnSetData(data);
        next(this.data);
    }

    public void doOnSetData(T data) {

    }

    /**
     * 刷新数据
     *
     * @return 可以监听本次刷新，或者不处理。
     */
    public void refresh() {
        getData().subscribe(newdata -> set(newdata), error -> {
        });
    }

    public Observable<T> refreshAction() {
        return getData().doOnNext(newdata -> set(newdata));
    }

    public Observable<T> getNotNullData() {
        T data = get();
        return data == null ? refreshAction() : Observable.just(data);
    }

    /**
     * 获取数据
     *
     * @return
     */
    protected abstract Observable<T> getData();

    private void next(T data) {
        publishSubject.onNext(data);
        behaviorSubject.onNext(data);
    }

}
