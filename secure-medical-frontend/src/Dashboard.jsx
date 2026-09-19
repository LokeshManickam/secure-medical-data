import { useState } from "react";

import Sidebar from "./components/Sidebar";
import Header from "./components/Header";

import DashboardPage from "./pages/Dashboard";
import Patients from "./pages/Patients";
import PatientAccess from "./pages/PatientAccess";
import SecureMessages from "./pages/SecureMessages";
import SecurityCenter from "./pages/SecurityCenter";
import Activity from "./pages/Activity";

import "./styles/Dashboard.css";
import "./styles/Pages.css";

function Dashboard() {

    const [activePage, setActivePage] =
        useState("dashboard");

    const [selectedPatient, setSelectedPatient] =
        useState(null);

    const username =
        localStorage.getItem("username") || "doctor";

    const role =
        localStorage.getItem("role") || "DOCTOR";

    const handleLogout = () => {

        localStorage.removeItem("token");
        localStorage.removeItem("username");
        localStorage.removeItem("role");

        window.location.reload();
    };

    const renderPage = () => {

        switch (activePage) {

            case "patients":

                return (
                    <Patients
                        setSelectedPatient={
                            setSelectedPatient
                        }
                        setActivePage={
                            setActivePage
                        }
                    />
                );

            case "access":

                return (
                    <PatientAccess
                        patient={selectedPatient}
                        setActivePage={
                            setActivePage
                        }
                    />
                );

            case "messages":

                return <SecureMessages />;

            case "security":

                return <SecurityCenter />;

            case "activity":

                return <Activity />;

            case "dashboard":

            default:

                return (
                    <DashboardPage
                        setActivePage={
                            setActivePage
                        }
                    />
                );
        }
    };

    return (

        <div className="app-layout">

            <Sidebar
                activePage={activePage}
                setActivePage={setActivePage}
            />

            <div className="main-area">

                <Header
                    activePage={activePage}
                    username={username}
                    role={role}
                    onLogout={handleLogout}
                />

                <main className="page-container">

                    {renderPage()}

                </main>

            </div>

        </div>
    );
}

export default Dashboard;