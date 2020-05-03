package com.calorie.mealtracker.model.request;

public class GetMealsForUserRequestBody {

    private long fromTimeInMilliseconds;
    private long toTimeInMilliseconds;

    public GetMealsForUserRequestBody(long fromTimeInMilliseconds, long toTimeInMilliseconds) {
        this.fromTimeInMilliseconds = fromTimeInMilliseconds;
        this.toTimeInMilliseconds = toTimeInMilliseconds;
    }

    public long getFromTimeInMilliseconds() {
        return fromTimeInMilliseconds;
    }

    public void setFromTimeInMilliseconds(long fromTimeInMilliseconds) {
        this.fromTimeInMilliseconds = fromTimeInMilliseconds;
    }

    public long getToTimeInMilliseconds() {
        return toTimeInMilliseconds;
    }

    public void setToTimeInMilliseconds(long toTimeInMilliseconds) {
        this.toTimeInMilliseconds = toTimeInMilliseconds;
    }
}
