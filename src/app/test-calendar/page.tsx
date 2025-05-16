"use client"

import { Calendar } from "@/app/components/calendar"
import { useState } from "react"
import { DateRange } from "react-day-picker"

export default function TestCalendarPage() {
  const [date, setDate] = useState<Date | undefined>(new Date())
  const [range, setRange] = useState<DateRange | undefined>({
    from: new Date(),
    to: undefined
  })

  return (
    <div className="min-h-screen p-10 space-y-8">
      <div>
        <h2 className="text-lg font-semibold mb-4">Single Date Selection</h2>
        <Calendar
          mode="single"
          selected={date}
          onSelect={setDate}
          className="rounded-md border"
        />
        <p className="mt-4">Selected date: {date?.toLocaleDateString()}</p>
      </div>

      <div>
        <h2 className="text-lg font-semibold mb-4">Date Range Selection</h2>
        <Calendar
          mode="range"
          selected={range}
          onSelect={setRange}
          className="rounded-md border"
        />
        <p className="mt-4">
          Selected range: {range?.from?.toLocaleDateString()} to{" "}
          {range?.to?.toLocaleDateString()}
        </p>
      </div>
    </div>
  )
}
