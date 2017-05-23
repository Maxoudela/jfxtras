package jfxtras.icalendarfx.parameters;

import java.util.HashMap;
import java.util.Map;

import jfxtras.icalendarfx.parameters.Range;
import jfxtras.icalendarfx.parameters.VParameterBase;
import jfxtras.icalendarfx.parameters.Range.RangeType;
import jfxtras.icalendarfx.utilities.StringConverter;

/**
 * RANGE
 * Recurrence Identifier Range
 * RFC 5545, 3.2.13, page 23
 * 
 * To specify the effective range of recurrence instances from
 *  the instance specified by the recurrence identifier specified by
 *  the property.
 * 
 * Example:
 * RECURRENCE-ID;RANGE=THISANDFUTURE:19980401T133000Z
 * 
 * @author David Bal
 *
 */
public class Range extends VParameterBase<Range, RangeType>
{
	private static final StringConverter<RangeType> CONVERTER = new StringConverter<RangeType>()
    {
        @Override
        public String toString(RangeType object)
        {
            return object.toString();
        }

        @Override
        public RangeType fromString(String string)
        {
            return RangeType.enumFromName(string.toUpperCase());
        }
    };
    
    /** Set THISANDFUTURE as default value */
    public Range()
    {
        super(RangeType.THIS_AND_FUTURE, CONVERTER);
    }
  
    public Range(RangeType value)
    {
        super(value, CONVERTER);
    }

    public Range(String content)
    {
        super(RangeType.enumFromName(content), CONVERTER);
    }
    
    public Range(Range source)
    {
        super(source, CONVERTER);
    }  
    
    public enum RangeType
    {
        THIS_AND_FUTURE ("THISANDFUTURE"),
        THIS_AND_PRIOR ("THISANDPRIOR"); // "THISANDPRIOR" is deprecated by this revision of iCalendar and MUST NOT be generated by applications.
        
        private static Map<String, RangeType> enumFromNameMap = makeEnumFromNameMap();
        private static Map<String, RangeType> makeEnumFromNameMap()
        {
            Map<String, RangeType> map = new HashMap<>();
            RangeType[] values = RangeType.values();
            for (int i=0; i<values.length; i++)
            {
                map.put(values[i].toString(), values[i]);
            }
            return map;
        }
        /** get enum from name */
        public static RangeType enumFromName(String propertyName)
        {
            return enumFromNameMap.get(propertyName.toUpperCase());
        }
        
        private String name;
        @Override public String toString() { return name; }
        RangeType(String name)
        {
            this.name = name;
        }
    }
    
    public static Range parse(String content)
    {
    	return Range.parse(new Range(), content);
    }
}