-- PostgreSQL Database Schema for AfyaMsaada Backend Synchronization

CREATE TABLE IF NOT EXISTS facilities (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    county VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL,
    contact_phone VARCHAR(50) NOT NULL,
    services TEXT NOT NULL,
    type VARCHAR(100) NOT NULL DEFAULT 'Public',
    is_verified BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS educational_resources (
    id SERIAL PRIMARY KEY,
    category VARCHAR(100) NOT NULL,
    title VARCHAR(255) NOT NULL,
    summary TEXT NOT NULL,
    full_content TEXT NOT NULL,
    health_risks TEXT NOT NULL,
    support_tips TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sync_logs (
    id SERIAL PRIMARY KEY,
    client_device_id VARCHAR(100) NOT NULL,
    sync_timestamp TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    records_synced INTEGER DEFAULT 0,
    status VARCHAR(50) NOT NULL DEFAULT 'SUCCESS'
);

-- Indexing for fast county and category queries
CREATE INDEX IF NOT EXISTS idx_facilities_county ON facilities(county);
CREATE INDEX IF NOT EXISTS idx_resources_category ON educational_resources(category);

-- Initial Seed Data
INSERT INTO facilities (name, county, location, contact_phone, services, type, is_verified) VALUES
('NACADA 1192 National Toll-Free Helpline', 'Nairobi', 'NBI HQ / Nationwide', '1192', '24/7 Telephone Counseling, Emergency Intervention & Referrals', 'Public Helpline', TRUE),
('Mathari National Teaching & Referral Hospital', 'Nairobi', 'Thika Road, Muthaiga, Nairobi', '0202337694', 'Inpatient Detoxification, Psychiatric Care & Rehabilitation', 'Public Hospital', TRUE),
('Coast General Teaching & Referral Hospital Rehab Unit', 'Mombasa', 'Kisauni, Mombasa', '0722207868', 'Outpatient Counseling, Methadone Maintenance & Addiction Care', 'Public Hospital', TRUE),
('Asumbi Treatment Centre', 'Homabay', 'Asumbi, Homa Bay County', '0720448833', 'Residential Rehabilitation, Group Therapy & Aftercare', 'NGO / Faith-Based', TRUE);

INSERT INTO educational_resources (category, title, summary, full_content, health_risks, support_tips) VALUES
('Alcohol', 'Understanding Alcohol Dependence & Recovery', 'Alcohol is a depressant affecting central nervous system.', 'Frequent heavy drinking can lead to physical dependence and organ complications.', 'Liver disease, memory loss, cardiovascular risk.', 'Seek medical support for withdrawal, engage in community groups.'),
('Cannabis', 'Cannabis Use, Health Impact & Harm Reduction', 'Cannabis alters mood and focus via THC.', 'Regular use can cause memory deficits and motivation changes.', 'Anxiety, respiratory issues.', 'Set limits, avoid mixing with stimulants.'),
('Prescription Opioids', 'Prescription Painkiller Safety & Opioid Risk', 'Opioids carry high tolerance and dependence risks.', 'Misuse increases overdose probability exponentially.', 'Respiratory depression, physical dependence.', 'Follow medical advice, never share prescription medications.');
