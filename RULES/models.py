from pydantic import BaseModel
from typing import List
import datetime

class Measurement(BaseModel):
    Date: datetime.datetime
    Origin: str
    Concentration: float

class MeasurementList(BaseModel):
    Measurements: List[Measurement]

class RangeDate(BaseModel):
    init_date: datetime.datetime
    end_date: datetime.datetime