package com.example.neti_back.utils;

import com.example.neti_back.dto.SessionDto;
import com.example.neti_back.entity.SessionSubject;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import java.time.LocalTime;

@Component
public class QueueMapper extends ModelMapper {
    public QueueMapper() {
        super();
        this.getConfiguration().setSkipNullEnabled(true);

        Converter<Long, LocalTime> longToLocalTimeConverter =
                ctx -> ctx.getSource() == null ? null : LocalTime.ofSecondOfDay(ctx.getSource());

        Converter<LocalTime, Long> localTimeToLongConverter =
                ctx -> ctx.getSource() == null ? null : (long) ctx.getSource().toSecondOfDay();

        this.addMappings(new PropertyMap<SessionSubject, SessionDto>() {
            @Override
            protected void configure() {
                using(longToLocalTimeConverter).map(source.getStartTime(), destination.getStartTime());
                using(longToLocalTimeConverter).map(source.getEndTime(), destination.getEndTime());
                map(source.getDay(), destination.getDay());
                map(source.getSessionSubject().getName(), destination.getSubjectName());
                map(source.getGroupName(), destination.getGroup());
                map(source.getWeeks(), destination.getWeeks());
                map(source.getQueueSubject().getId(), destination.getQueueSubject());
            }
        });

        this.addMappings(new PropertyMap<SessionDto, SessionSubject>() {
            @Override
            protected void configure() {
                using(localTimeToLongConverter).map(source.getStartTime(), destination.getStartTime());
                using(localTimeToLongConverter).map(source.getEndTime(), destination.getEndTime());
                map(source.getDay(), destination.getDay());
                map(source.getGroup(), destination.getGroupName());
                map(source.getWeeks(), destination.getWeeks());
                map(source.getQueueSubject(), destination.getQueueSubject().getId());
            }
        });
    }
}
