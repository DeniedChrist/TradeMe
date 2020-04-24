package crypto.access;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import crypto.config.SessionConfig;
import crypto.model.Marker;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class MarkerAccess {

	@Autowired
	private SessionConfig sf;

	public MarkerAccess() {
		super();
		System.out.println("In Marker access");
	}

	public Integer addMarker(Marker marker) {
		return (Integer) sf.getNewSession().save(marker);
	}

	public void updateMarker(Marker marker) {
		sf.getNewSession().update(marker);
	}

}
