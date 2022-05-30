package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    void testMapToBoards() {
        //Given
        List<TrelloListDto> trelloListsDto1 = new ArrayList<>();
        List<TrelloListDto> trelloListsDto2 = new ArrayList<>();
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("22", "Trello board DTO 1 name", trelloListsDto1);
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("33", "Trello board DTO 2 name", trelloListsDto2);
        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
        trelloBoardsDto.add(trelloBoardDto1);
        trelloBoardsDto.add(trelloBoardDto2);

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardsDto);

        //Then
        assertEquals(2, trelloBoards.size());
        assertEquals("22", trelloBoards.get(0).getId());
        assertEquals("Trello board DTO 2 name", trelloBoards.get(1).getName());
        assertEquals(0, trelloBoards.get(0).getLists().size());
    }

    @Test
    void testMapToBoardsDto() {
        List<TrelloList> trelloLists1 = new ArrayList<>();
        List<TrelloList> trelloLists2 = new ArrayList<>();
        TrelloBoard trelloBoard1 = new TrelloBoard("22", "Trello board 1 name", trelloLists1);
        TrelloBoard trelloBoard2 = new TrelloBoard("33", "Trello board 2 name", trelloLists2);
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);

        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        assertEquals(2, trelloBoardsDto.size());
        assertEquals("22", trelloBoardsDto.get(0).getId());
        assertEquals("Trello board 2 name", trelloBoardsDto.get(1).getName());
        assertEquals(0, trelloBoardsDto.get(0).getLists().size());
    }

    @Test
    void testMapToList() {
        //Given
        TrelloListDto trelloListDto1 = new TrelloListDto("11", "Trello list DTO 1 name", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("12", "Trello list DTO 2 name", true);
        List<TrelloListDto> trelloListsDto = new ArrayList<>();
        trelloListsDto.add(trelloListDto1);
        trelloListsDto.add(trelloListDto2);

        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListsDto);

        //Then
        assertEquals(2, trelloLists.size());
        assertEquals("11", trelloLists.get(0).getId());
        assertEquals("Trello list DTO 2 name", trelloLists.get(1).getName());
        assertFalse(trelloLists.get(0).isClosed());
        assertTrue(trelloLists.get(1).isClosed());
    }

    @Test
    void testMapToListDto() {
        //Given
        TrelloList trelloList1 = new TrelloList("11", "Trello list 1 name", false);
        TrelloList trelloList2 = new TrelloList("12", "Trello list 2 name", true);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList1);
        trelloLists.add(trelloList2);

        //When
        List<TrelloListDto> trelloListsDto = trelloMapper.mapToListDto(trelloLists);

        //Then
        assertEquals(2, trelloListsDto.size());
        assertEquals("11", trelloListsDto.get(0).getId());
        assertEquals("Trello list 2 name", trelloListsDto.get(1).getName());
        assertFalse(trelloListsDto.get(0).isClosed());
        assertTrue(trelloListsDto.get(1).isClosed());
    }

    @Test
    void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card name", "Card description", "top", "9");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals("Card name", trelloCard.getName());
        assertEquals("Card description", trelloCard.getDescription());
        assertEquals("top", trelloCard.getPos());
        assertEquals("9", trelloCard.getListId());
    }

    @Test
    void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Card DTO name", "Card DTO description", "top", "9");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals("Card DTO name", trelloCardDto.getName());
        assertEquals("Card DTO description", trelloCardDto.getDescription());
        assertEquals("top", trelloCardDto.getPos());
        assertEquals("9", trelloCardDto.getListId());
    }
}
