package BookingAPITest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	

}
