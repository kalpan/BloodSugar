package com.sugar.dto;

import java.util.concurrent.atomic.AtomicLong;

public class BloodSugar {
	private static AtomicLong idGenerator = new AtomicLong(1);
	private Double bloodSugar;
	private Long ts;
	private Long id;
	
	public BloodSugar(Double bloodSugar, Long ts) {
		super();
		this.bloodSugar = bloodSugar;
		this.ts = ts;
		this.id = BloodSugar.idGenerator.incrementAndGet();
	}

	public Double getBloodSugar() {
		return bloodSugar;
	}

	public void setBloodSugar(Double bloodSugar) {
		this.bloodSugar = bloodSugar;
	}

	public Long getTs() {
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bloodSugar == null) ? 0 : bloodSugar.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ts == null) ? 0 : ts.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BloodSugar other = (BloodSugar) obj;
		if (bloodSugar == null) {
			if (other.bloodSugar != null)
				return false;
		} else if (!bloodSugar.equals(other.bloodSugar))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ts == null) {
			if (other.ts != null)
				return false;
		} else if (!ts.equals(other.ts))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BloodSugar [bloodSugar=" + bloodSugar + ", ts=" + ts + ", id=" + id + "]";
	}
	
}
