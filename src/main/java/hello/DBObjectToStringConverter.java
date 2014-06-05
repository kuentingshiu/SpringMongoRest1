package hello;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import com.mongodb.DBObject;

@WritingConverter
public class DBObjectToStringConverter implements Converter<DBObject, String> {

	  public String convert(DBObject source) {
		    return source == null ? null : source.toString();
		  }
	
}
