package com.togedog.friend.mapper;

import com.togedog.friend.dto.Dto;
import com.togedog.friend.entity.Friend;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-20T16:26:51+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.22 (Azul Systems, Inc.)"
)
@Component
public class FriendMapperImpl implements FriendMapper {

    @Override
    public Dto.Response friendToResponse(Friend friend) {
        if ( friend == null ) {
            return null;
        }

        Dto.Response response = new Dto.Response();

        response.setFriendId( friend.getFriendId() );

        return response;
    }

    @Override
    public List<Dto.Response> friendsToResponse(List<Friend> friends) {
        if ( friends == null ) {
            return null;
        }

        List<Dto.Response> list = new ArrayList<Dto.Response>( friends.size() );
        for ( Friend friend : friends ) {
            list.add( friendToResponse( friend ) );
        }

        return list;
    }
}
