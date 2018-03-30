package ru.asmirnov.accservice.dto;

import java.util.Objects;

/**
 * @author Alexey Smirnov at 29/03/2018
 */
public class AccountDetailsRequestDto {

    private Long id;

    public AccountDetailsRequestDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDetailsRequestDto that = (AccountDetailsRequestDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
