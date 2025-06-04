import React, { useState, useEffect } from 'react';

export default function BookingPage() {
  const [startDate, setStartDate] = useState('');
  const [startTime, setStartTime] = useState('');
  const [endDate, setEndDate] = useState('');
  const [endTime, setEndTime] = useState('');
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [showSummary, setShowSummary] = useState(false);

  // Styles
  const styles = {
    body: {
      fontFamily: '-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell, sans-serif',
      minHeight: '100vh',
      background: 'linear-gradient(135deg, #e0f2fe 0%, #e8eaf6 100%)',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      padding: '20px',
      margin: 0
    },
    container: {
      background: 'white',
      borderRadius: '16px',
      boxShadow: '0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04)',
      padding: '32px',
      maxWidth: '700px',
      width: '100%'
    },
    header: {
      textAlign: 'center',
      marginBottom: '32px'
    },
    headerTitle: {
      fontSize: '2rem',
      fontWeight: 'bold',
      color: '#1f2937',
      marginBottom: '8px'
    },
    headerSubtitle: {
      color: '#6b7280',
      fontSize: '1rem'
    },
    formGrid: {
      display: 'grid',
      gridTemplateColumns: '1fr 1fr',
      gap: '24px',
      marginBottom: '24px'
    },
    section: {
      display: 'flex',
      flexDirection: 'column',
      gap: '16px'
    },
    sectionTitle: {
      fontSize: '1.125rem',
      fontWeight: '600',
      color: '#374151',
      display: 'flex',
      alignItems: 'center',
      gap: '8px'
    },
    icon: {
      width: '20px',
      height: '20px',
      color: '#2563eb'
    },
    inputGroup: {
      display: 'flex',
      flexDirection: 'column',
      gap: '12px'
    },
    inputField: {
      display: 'flex',
      flexDirection: 'column'
    },
    label: {
      fontSize: '0.875rem',
      fontWeight: '500',
      color: '#6b7280',
      marginBottom: '4px'
    },
    input: {
      padding: '12px 16px',
      border: '2px solid #d1d5db',
      borderRadius: '8px',
      fontSize: '1rem',
      transition: 'all 0.2s ease',
      outline: 'none'
    },
    inputFocus: {
      borderColor: '#2563eb',
      boxShadow: '0 0 0 3px rgba(37, 99, 235, 0.1)'
    },
    summary: {
      background: '#eff6ff',
      border: '2px solid #bfdbfe',
      borderRadius: '8px',
      padding: '16px',
      marginBottom: '24px'
    },
    summaryTitle: {
      fontWeight: '600',
      color: '#1e40af',
      marginBottom: '8px'
    },
    summaryContent: {
      fontSize: '0.875rem',
      color: '#1e40af'
    },
    summaryText: {
      marginBottom: '4px'
    },
    submitBtn: {
      width: '100%',
      background: 'linear-gradient(135deg, #2563eb 0%, #4f46e5 100%)',
      color: 'white',
      padding: '16px 24px',
      border: 'none',
      borderRadius: '8px',
      fontSize: '1.125rem',
      fontWeight: '600',
      cursor: 'pointer',
      transition: 'all 0.2s ease',
      boxShadow: '0 4px 6px -1px rgba(0, 0, 0, 0.1)'
    },
    successContainer: {
      textAlign: 'center',
      maxWidth: '400px'
    },
    successIcon: {
      width: '64px',
      height: '64px',
      color: '#10b981',
      margin: '0 auto 16px'
    },
    successTitle: {
      fontSize: '1.5rem',
      fontWeight: 'bold',
      color: '#1f2937',
      marginBottom: '16px'
    },
    bookingDetails: {
      background: '#f9fafb',
      borderRadius: '8px',
      padding: '16px',
      marginBottom: '24px',
      textAlign: 'left'
    },
    detailItem: {
      marginBottom: '12px'
    },
    detailLabel: {
      fontWeight: '600',
      color: '#6b7280'
    },
    detailValue: {
      color: '#1f2937',
      marginTop: '2px'
    }
  };

  // Get today's date for minimum date
  const today = new Date().toISOString().split('T')[0];

  // Generate 30-minute time intervals
  const generateTimeOptions = () => {
    const times = [];
    for (let hour = 0; hour < 24; hour++) {
      for (let minute = 0; minute < 60; minute += 30) {
        const hourStr = hour.toString().padStart(2, '0');
        const minuteStr = minute.toString().padStart(2, '0');
        const timeValue = `${hourStr}:${minuteStr}`;
        const displayTime = new Date(`2000-01-01T${timeValue}`).toLocaleTimeString([], {
          hour: 'numeric',
          minute: '2-digit',
          hour12: true
        });
        times.push({ value: timeValue, display: displayTime });
      }
    }
    return times;
  };

  const timeOptions = generateTimeOptions();

  // Update summary when fields change
  useEffect(() => {
    updateSummary();
  }, [startDate, startTime, endDate, endTime]);

  const updateSummary = () => {
    if (startDate && startTime && endDate && endTime) {
      const startDateTime = new Date(`${startDate}T${startTime}`);
      const endDateTime = new Date(`${endDate}T${endTime}`);
      
      if (endDateTime > startDateTime) {
        setShowSummary(true);
      } else {
        setShowSummary(false);
      }
    } else {
      setShowSummary(false);
    }
  };

  const getDuration = () => {
    if (startDate && startTime && endDate && endTime) {
      const startDateTime = new Date(`${startDate}T${startTime}`);
      const endDateTime = new Date(`${endDate}T${endTime}`);
      const duration = (endDateTime - startDateTime) / (1000 * 60 * 60);
      const days = Math.floor(duration / 24);
      const hours = Math.floor(duration % 24);

      let durationText = '';
      if (days > 0) {
        durationText = `${days} day${days > 1 ? 's' : ''}`;
        if (hours > 0) {
          durationText += ` and ${hours} hour${hours > 1 ? 's' : ''}`;
        }
      } else {
        durationText = `${hours} hour${hours > 1 ? 's' : ''}`;
      }
      return durationText;
    }
    return '';
  };

  const handleSubmit = () => {
    // Validation
    if (!startDate || !startTime || !endDate || !endTime) {
      alert('Please fill in all fields');
      return;
    }

    const startDateTime = new Date(`${startDate}T${startTime}`);
    const endDateTime = new Date(`${endDate}T${endTime}`);

    if (endDateTime <= startDateTime) {
      alert('End date/time must be after start date/time');
      return;
    }

    // Process booking (you can add your API call here)
    console.log({
      startDate,
      startTime,
      endDate,
      endTime,
      startDateTime,
      endDateTime
    });

    setIsSubmitted(true);
  };

  const resetForm = () => {
    setStartDate('');
    setStartTime('');
    setEndDate('');
    setEndTime('');
    setIsSubmitted(false);
    setShowSummary(false);
  };

  if (isSubmitted) {
    return (
      <div style={styles.body}>
        <div style={styles.container}>
          <div style={styles.successContainer}>
            <svg style={styles.successIcon} fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
            </svg>
            <h2 style={styles.successTitle}>Booking Confirmed!</h2>
            <div style={styles.bookingDetails}>
              <div style={styles.detailItem}>
                <span style={styles.detailLabel}>Start:</span>
                <p style={styles.detailValue}>{new Date(`${startDate}T${startTime}`).toLocaleString()}</p>
              </div>
              <div style={styles.detailItem}>
                <span style={styles.detailLabel}>End:</span>
                <p style={styles.detailValue}>{new Date(`${endDate}T${endTime}`).toLocaleString()}</p>
              </div>
            </div>
            <button
              style={styles.submitBtn}
              onClick={resetForm}
              onMouseOver={(e) => {
                e.target.style.background = 'linear-gradient(135deg, #1d4ed8 0%, #4338ca 100%)';
                e.target.style.transform = 'translateY(-1px)';
              }}
              onMouseOut={(e) => {
                e.target.style.background = 'linear-gradient(135deg, #2563eb 0%, #4f46e5 100%)';
                e.target.style.transform = 'translateY(0)';
              }}
            >
              Make Another Booking
            </button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div style={styles.body}>
      <div style={styles.container}>
        <div style={styles.header}>
          <h1 style={styles.headerTitle}>Make a Booking</h1>
          <p style={styles.headerSubtitle}>Select your preferred date and time</p>
        </div>

        <div style={styles.formGrid}>
          {/* Start Date and Time */}
          <div style={styles.section}>
            <div style={styles.sectionTitle}>
              <svg style={styles.icon} fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
              </svg>
              Start Date & Time
            </div>
            
            <div style={styles.inputGroup}>
              <div style={styles.inputField}>
                <label style={styles.label}>Date</label>
                <input
                  type="date"
                  value={startDate}
                  onChange={(e) => setStartDate(e.target.value)}
                  min={today}
                  style={styles.input}
                  onFocus={(e) => e.target.style.borderColor = '#2563eb'}
                  onBlur={(e) => e.target.style.borderColor = '#d1d5db'}
                  required
                />
              </div>
              
              <div style={styles.inputField}>
                <label style={styles.label}>Time</label>
                <select
                  value={startTime}
                  onChange={(e) => setStartTime(e.target.value)}
                  style={styles.input}
                  onFocus={(e) => e.target.style.borderColor = '#2563eb'}
                  onBlur={(e) => e.target.style.borderColor = '#d1d5db'}
                  required
                >
                  <option value="">Select time</option>
                  {timeOptions.map((time) => (
                    <option key={time.value} value={time.value}>
                      {time.display}
                    </option>
                  ))}
                </select>
              </div>
            </div>
          </div>

          {/* End Date and Time */}
          <div style={styles.section}>
            <div style={styles.sectionTitle}>
              <svg style={styles.icon} fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
              End Date & Time
            </div>
            
            <div style={styles.inputGroup}>
              <div style={styles.inputField}>
                <label style={styles.label}>Date</label>
                <input
                  type="date"
                  value={endDate}
                  onChange={(e) => setEndDate(e.target.value)}
                  min={startDate || today}
                  style={styles.input}
                  onFocus={(e) => e.target.style.borderColor = '#2563eb'}
                  onBlur={(e) => e.target.style.borderColor = '#d1d5db'}
                  required
                />
              </div>
              
              <div style={styles.inputField}>
                <label style={styles.label}>Time</label>
                <select
                  value={endTime}
                  onChange={(e) => setEndTime(e.target.value)}
                  style={styles.input}
                  onFocus={(e) => e.target.style.borderColor = '#2563eb'}
                  onBlur={(e) => e.target.style.borderColor = '#d1d5db'}
                  required
                >
                  <option value="">Select time</option>
                  {timeOptions.map((time) => (
                    <option key={time.value} value={time.value}>
                      {time.display}
                    </option>
                  ))}
                </select>
              </div>
            </div>
          </div>
        </div>

        {/* Booking Summary */}
        {showSummary && (
          <div style={styles.summary}>
            <h4 style={styles.summaryTitle}>Booking Summary</h4>
            <div style={styles.summaryContent}>
              <p style={styles.summaryText}>
                <strong>From:</strong> {new Date(`${startDate}T${startTime}`).toLocaleString()}
              </p>
              <p style={styles.summaryText}>
                <strong>To:</strong> {new Date(`${endDate}T${endTime}`).toLocaleString()}
              </p>
              <p style={styles.summaryText}>
                <strong>Duration:</strong> {getDuration()}
              </p>
            </div>
          </div>
        )}

        <button
          style={styles.submitBtn}
          onClick={handleSubmit}
          onMouseOver={(e) => {
            e.target.style.background = 'linear-gradient(135deg, #1d4ed8 0%, #4338ca 100%)';
            e.target.style.transform = 'translateY(-1px)';
          }}
          onMouseOut={(e) => {
            e.target.style.background = 'linear-gradient(135deg, #2563eb 0%, #4f46e5 100%)';
            e.target.style.transform = 'translateY(0)';
          }}
        >
          Confirm Booking
        </button>
      </div>
    </div>
  );
}