--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4 (Debian 17.4-1.pgdg120+2)
-- Dumped by pg_dump version 17.4 (Debian 17.4-1.pgdg120+2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: queue_subject; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.queue_subject (
    id uuid NOT NULL,
    open_places character varying(255)
);


ALTER TABLE public.queue_subject OWNER TO "user";

--
-- Name: session_subject; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.session_subject (
    id uuid NOT NULL,
    day character varying(255),
    end_time bigint,
    group_name character varying(255),
    room character varying(255),
    start_time bigint,
    weeks character varying(255),
    queue_subject_id uuid,
    session_subject_id uuid,
    CONSTRAINT session_subject_day_check CHECK (((day)::text = ANY ((ARRAY['MONDAY'::character varying, 'TUESDAY'::character varying, 'WEDNESDAY'::character varying, 'THURSDAY'::character varying, 'FRIDAY'::character varying, 'SATURDAY'::character varying, 'SUNDAY'::character varying])::text[])))
);


ALTER TABLE public.session_subject OWNER TO "user";

--
-- Name: subject; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.subject (
    id uuid NOT NULL,
    name character varying(255),
    type character varying(255),
    CONSTRAINT subject_type_check CHECK (((type)::text = ANY ((ARRAY['LECTURE'::character varying, 'PRACTICE'::character varying, 'LAB'::character varying])::text[])))
);


ALTER TABLE public.subject OWNER TO "user";

--
-- Data for Name: queue_subject; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.queue_subject (id, open_places) FROM stdin;
2e97222b-a1aa-4329-a7d7-66902a19282b	5=Unknown,3=Unknown
6dc66a8e-ef5b-420a-be11-ea3c4286c4e7	3=Unknown
221101eb-85d6-4da6-aa23-82de04843494	\N
7ba6799f-11f4-49d8-b2dd-9b33f3d8aba0	2=Unknown
\.


--
-- Data for Name: session_subject; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.session_subject (id, day, end_time, group_name, room, start_time, weeks, queue_subject_id, session_subject_id) FROM stdin;
6d315083-5657-4d76-9475-3130dc6447f0	MONDAY	36000	PМИ-22	1-402	30600	1,3,5,7	2e97222b-a1aa-4329-a7d7-66902a19282b	2b626d32-668c-4ffd-8c30-2612e46e5f2d
dee848da-7671-43ee-bb6b-228918d06929	MONDAY	36000	PМИ-22	1-402	30600	1,3,5,7	6dc66a8e-ef5b-420a-be11-ea3c4286c4e7	bd9ccf2e-59a9-4a17-aed4-f3aeed7a8be3
626cab1e-c31e-42b3-938e-c4855ca4b77c	MONDAY	36000	PМИ-22	1-402	30600	1,3,5,7	7ba6799f-11f4-49d8-b2dd-9b33f3d8aba0	cf813f68-e846-45eb-a11c-d2357f3f2f3b
a095f117-169b-44e0-9453-2bb80a55cafa	MONDAY	36000	PМИ-22	1-402	30600	1,3,5,7	221101eb-85d6-4da6-aa23-82de04843494	840f52cd-764d-4348-aaa5-23bd2d3e15fe
\.


--
-- Data for Name: subject; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.subject (id, name, type) FROM stdin;
2b626d32-668c-4ffd-8c30-2612e46e5f2d	rus2	\N
bd9ccf2e-59a9-4a17-aed4-f3aeed7a8be3	rus2	\N
cf813f68-e846-45eb-a11c-d2357f3f2f3b	Математический анализ	\N
840f52cd-764d-4348-aaa5-23bd2d3e15fe	Линейная алгебра	\N
\.


--
-- Name: queue_subject queue_subject_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.queue_subject
    ADD CONSTRAINT queue_subject_pkey PRIMARY KEY (id);


--
-- Name: session_subject session_subject_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.session_subject
    ADD CONSTRAINT session_subject_pkey PRIMARY KEY (id);


--
-- Name: subject subject_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.subject
    ADD CONSTRAINT subject_pkey PRIMARY KEY (id);


--
-- Name: session_subject uk8473ni3edyro1nssrlqhplwy; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.session_subject
    ADD CONSTRAINT uk8473ni3edyro1nssrlqhplwy UNIQUE (queue_subject_id);


--
-- Name: session_subject ukmmof7y2j73ufe2po9lq89ehrl; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.session_subject
    ADD CONSTRAINT ukmmof7y2j73ufe2po9lq89ehrl UNIQUE (session_subject_id);


--
-- Name: session_subject fkgrabfyrhiibfks4ch32dbr9j0; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.session_subject
    ADD CONSTRAINT fkgrabfyrhiibfks4ch32dbr9j0 FOREIGN KEY (session_subject_id) REFERENCES public.subject(id);


--
-- Name: session_subject fkn0libdir0itm1dhjt5motlddj; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.session_subject
    ADD CONSTRAINT fkn0libdir0itm1dhjt5motlddj FOREIGN KEY (queue_subject_id) REFERENCES public.queue_subject(id);


--
-- PostgreSQL database dump complete
--

