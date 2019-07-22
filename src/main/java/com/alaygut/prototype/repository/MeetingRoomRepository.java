package com.alaygut.prototype.repository;



import com.alaygut.prototype.domain.RoomFeature;
import org.springframework.data.repository.CrudRepository;

import com.alaygut.prototype.domain.Building;
import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.RecordState;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface MeetingRoomRepository extends CrudRepository<MeetingRoom, Long> {
	Iterable<MeetingRoom> findAllByStateEquals(RecordState state);
	Iterable<MeetingRoom> findByBuilding(Building building);
	Iterable<MeetingRoom> findAllByCapacityGreaterThanEqual(int capacity);
}
