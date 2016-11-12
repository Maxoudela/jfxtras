package jfxtras.icalendarfx.properties.component.recurrence.rrule.byxxx;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * By Minute 
 * BYMINUTE
 * RFC 5545, iCalendar 3.3.10, page 41
 * 
 * The BYMINUTE rule part specifies a COMMA-separated list of minutes within an hour.
 * Valid values are 0 to 59.
 *   
 * The BYSECOND, BYMINUTE and BYHOUR rule parts MUST NOT be specified
 * when the associated "DTSTART" property has a DATE value type.
 * These rule parts MUST be ignored in RECUR value that violate the
 * above requirement (e.g., generated by applications that pre-date
 * this revision of iCalendar).
 *
 * @author David Bal
 * 
 */
public class ByMinute extends ByRuleIntegerAbstract<ByMinute>
{
    public ByMinute()
    {
        super();
    }
    
    public ByMinute(Integer... minutes)
    {
        super(minutes);
    }
    
    public ByMinute(ByMinute source)
    {
        super(source);
    }
    
    @Override
    Predicate<Integer> isValidValue()
    {
        return (value) -> (value >= 0) && (value <= 59);
    }
    
    @Override
    public Stream<Temporal> streamRecurrences(Stream<Temporal> inStream, ChronoUnit chronoUnit, Temporal dateTimeStart)
    {
        if (dateTimeStart.isSupported(ChronoField.MINUTE_OF_HOUR))
        {
            switch (chronoUnit)
            {
            case MINUTES:
            case SECONDS:
                return inStream.filter(d ->
                        { // filter out all but qualifying hours
                            int myMinuteOfHour = d.get(ChronoField.MINUTE_OF_HOUR);
                            for (int minuteOfHour : getValue())
                            {
                                if (minuteOfHour > 0)
                                {
                                    if (minuteOfHour == myMinuteOfHour) return true;
                                }
                            }
                            return false;
                        });
            case HOURS:
            case DAYS:
            case WEEKS:
            case MONTHS:
            case YEARS:
                return inStream.flatMap(d -> 
                { // Expand to be include all hours of day
                    List<Temporal> dates = new ArrayList<>();
                    for (int minuteOfHour : getValue())
                    {
                        Temporal newTemporal = d.with(ChronoField.MINUTE_OF_HOUR, minuteOfHour);
//                        if (! DateTimeUtilities.isBefore(newTemporal, dateTimeStart))
//                        {
                            dates.add(newTemporal);
//                        }
                    }
                    return dates.stream();
                });
            default:
                throw new IllegalArgumentException("Not implemented: " + chronoUnit);
            }
        } else
        {
            return inStream; // ignore rule when not supported (RFC 5545 requirement)
        }
    }

    public static ByMinute parse(String content)
    {
        ByMinute element = new ByMinute();
        element.parseContent(content);
        return element;
    }
}