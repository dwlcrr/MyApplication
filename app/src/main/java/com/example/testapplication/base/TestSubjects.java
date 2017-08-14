package com.example.test.myapplication;

import rx.subjects.BehaviorSubject;

/**
 * Created by dongwanlin on 2016/12/27.
 */

public class TestSubjects {
    public static void main(String[] args) {

//        PublishSubject<Integer> subject = PublishSubject.create();
//        subject.onNext(1);
//        subject.subscribe(System.out::println);
//        subject.subscribe(new Action1<Integer>() {
//            @Override
//            public void call(Integer integer) {
//                System.out.println(integer);
//            }
//        });
//        subject.onNext(2);
//        subject.onNext(3);
//        subject.subscribe(System.out::println);
//        subject.onNext(4);

        BehaviorSubject<Integer> s = BehaviorSubject.create();
        s.onNext(0);
        s.onNext(1);
        s.onNext(2);
//        s.onCompleted();
        s.subscribe(
                v -> System.out.println("Late: " + v),
                e -> System.out.println("Error"),
                () -> System.out.println("Completed")
        );
        s.onNext(3);
        s.onNext(4);

//        BehaviorSubject<Integer> s = BehaviorSubject.create(0);
//        s.subscribe(v -> System.out.println(v));
//        s.subscribe(System.out::println);
//        s.onNext(1);
    }
}
