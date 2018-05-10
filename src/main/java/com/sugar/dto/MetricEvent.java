package com.sugar.dto;

import com.sugar.metadata.MetricEnum;

public abstract class MetricEvent {
	
	public MetricEvent() {
		this.ts = System.currentTimeMillis();
	}
	
	public MetricEvent(String metricName, Long metricValue) {
		this.ts = System.currentTimeMillis();
		this.metricName = metricName;
		this.metricValue = metricValue;
	}
	
	protected String metricName;
	protected MetricEnum metricType;
	protected Long metricValue;
	protected Long ts;
	public String getMetricName() {
		return metricName;
	}

	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

	public MetricEnum getMetricType() {
		return metricType;
	}

	public void setMetricType(MetricEnum metricType) {
		this.metricType = metricType;
	}

	public Long getMetricValue() {
		return metricValue;
	}

	public void setMetricValue(Long metricValue) {
		this.metricValue = metricValue;
	}

	public Long getTs() {
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((metricName == null) ? 0 : metricName.hashCode());
		result = prime * result + ((metricType == null) ? 0 : metricType.hashCode());
		result = prime * result + ((metricValue == null) ? 0 : metricValue.hashCode());
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
		MetricEvent other = (MetricEvent) obj;
		if (metricName == null) {
			if (other.metricName != null)
				return false;
		} else if (!metricName.equals(other.metricName))
			return false;
		if (metricType != other.metricType)
			return false;
		if (metricValue == null) {
			if (other.metricValue != null)
				return false;
		} else if (!metricValue.equals(other.metricValue))
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
		return "MetricEvent [metricName=" + metricName + ", metricType=" + metricType + ", metricValue=" + metricValue
				+ ", ts=" + ts + "]";
	}
	
}
