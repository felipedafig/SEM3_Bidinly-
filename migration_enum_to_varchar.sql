-- Migration script to change enum columns to VARCHAR
-- Run this in DataGrip/PostgreSQL to update your database schema

-- First, alter the Property table Status column
ALTER TABLE "Property" 
ALTER COLUMN "Status" TYPE VARCHAR(50) 
USING "Status"::text;

-- Then, alter the Bid table Status column  
ALTER TABLE "Bid"
ALTER COLUMN "Status" TYPE VARCHAR(50)
USING "Status"::text;

-- Drop the enum types (optional, but recommended to clean up)
DROP TYPE IF EXISTS property_status;
DROP TYPE IF EXISTS bid_status;

-- Verify the changes
SELECT column_name, data_type 
FROM information_schema.columns 
WHERE table_name = 'Property' AND column_name = 'Status';

SELECT column_name, data_type 
FROM information_schema.columns 
WHERE table_name = 'Bid' AND column_name = 'Status';

