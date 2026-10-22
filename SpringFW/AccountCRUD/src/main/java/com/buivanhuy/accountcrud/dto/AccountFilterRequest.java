package com.buivanhuy.accountcrud.dto;

import com.buivanhuy.accountcrud.enums.Gender;
import com.buivanhuy.accountcrud.enums.PositionName;
import lombok.Getter;

import java.time.LocalDate;

public record AccountFilterRequest(
   String search,
   Gender gender,
   PositionName positionName,
   Long departmentId,
   Long positionId,
   LocalDate createdFrom,
   LocalDate createdTo
) {}
