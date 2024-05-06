package com.user.userprofileservice.aspects;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.NonNullApi;

import java.util.Objects;

@Log4j2
public class PerformanceTrackerHandler implements ObservationHandler<Observation.Context> {
    @Override
    public void onStart(Observation.Context context) {
        log.trace("execution started {}", context.getName());
        context.put("time", System.currentTimeMillis());
    }

    @Override
    public void onError(Observation.Context context) {
        log.error("Error occurred {}", Objects.requireNonNull(context.getError()).getMessage());
    }

    @Override
    public void onEvent(Observation.Event event, Observation.Context context) {
        ObservationHandler.super.onEvent(event, context);
    }

    @Override
    public void onScopeOpened(Observation.Context context) {
        ObservationHandler.super.onScopeOpened(context);
    }

    @Override
    public void onScopeClosed(Observation.Context context) {
        ObservationHandler.super.onScopeClosed(context);
    }

    @Override
    public void onScopeReset(Observation.Context context) {
        ObservationHandler.super.onScopeReset(context);
    }

    @Override
    public void onStop(Observation.Context context) {
        log.info(
                "execution stopper"
                    +context.getName()
                    +"duration"
                +(System.currentTimeMillis()-context.getOrDefault("time",0L)
        ));
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return false;
    }
}