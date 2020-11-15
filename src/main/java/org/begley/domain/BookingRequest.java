package org.begley.domain;

public class BookingRequest {
    public Long id;
    public String apptDate;

    @Override
    public String toString() {
        return "BookingRequest [apptDate=" + apptDate + ", id=" + id + "]";
    }

    
}
