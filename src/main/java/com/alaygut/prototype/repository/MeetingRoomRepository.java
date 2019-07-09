package com.alaygut.prototype.repository;



import org.springframework.data.repository.CrudRepository;

import com.alaygut.prototype.domain.Building;
import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.RecordState;

public interface MeetingRoomRepository extends CrudRepository<MeetingRoom, Long> {
	Iterable<MeetingRoom> findAllByStateEquals(RecordState state);
	Iterable<MeetingRoom> findByBuilding(Building building);
}
