package com.romanovich.user.dto;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderDtoTest {
    @Test
    public void newOrderDtoTest() {
        OrderDTO dto = new OrderDTO();
        assertThat(dto.getMovies()).isEmpty();
        assertThat(dto.getCount()).isEmpty();
        assertThat(dto.getName()).isEqualTo("");
        assertThat(dto.getAddress()).isEqualTo("");
        assertThat(dto.getPhone()).isEqualTo("");
        assertThat(dto.getSummaryCost()).isEqualTo(0L);
    }
}
