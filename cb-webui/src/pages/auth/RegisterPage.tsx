import { useState } from "react";
import { useNavigate } from "react-router-dom";
import ContactStep from "../../components/auth/ContactStep";
import VerifyOtpStep from "../../components/auth/VerifyOtpStep";
import CompleteProfileForm from "../../components/auth/CompleteProfileForm";
import { useTheme } from "../../hooks/useTheme";
import { Sun, Moon } from "lucide-react";

type RegisterStep = 1 | 2 | 3;

export default function RegisterPage() {
  const [step, setStep] = useState<RegisterStep>(1);
  const [identifier, setIdentifier] = useState<string>("");
  const navigate = useNavigate();
  const { theme, toggleTheme } = useTheme();

  const handleContactSuccess = (id: string) => {
    setIdentifier(id);
    setStep(2);
  };

  const handleVerificationSuccess = (newUser: boolean) => {
    if (newUser) {
      setStep(3);
    } else {
      navigate("/home");
    }
  };

  return (
    <div className="min-h-screen w-full flex items-center justify-center p-4 bg-theme-bg relative overflow-hidden transition-colors duration-300">
      {/* Floating Theme Toggle */}
      <button
        onClick={toggleTheme}
        className="absolute top-6 right-6 p-3 rounded-full bg-theme-card border border-theme-border text-theme-text hover:bg-theme-bg transition-all duration-300 shadow-md cursor-pointer z-20 flex items-center justify-center"
        aria-label="Toggle theme"
      >
        {theme === "dark" ? (
          <Sun className="w-5 h-5 text-yellow-400" />
        ) : (
          <Moon className="w-5 h-5 text-[#7B1E3A]" />
        )}
      </button>

      {/* Premium Decorative Ambient Background Blobs */}
      <div className="absolute -top-40 -left-40 w-96 h-96 rounded-full bg-theme-primary/10 blur-3xl pointer-events-none" />
      <div className="absolute -bottom-40 -right-40 w-96 h-96 rounded-full bg-theme-accent/10 blur-3xl pointer-events-none" />
      <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[500px] h-[500px] rounded-full bg-theme-secondary/5 blur-3xl pointer-events-none" />

      <div className="w-full max-w-md z-10 transition-all duration-500">
        {step === 1 && (
          <ContactStep
            onSuccess={handleContactSuccess}
            initialIdentifier={identifier}
          />
        )}

        {step === 2 && (
          <VerifyOtpStep
            identifier={identifier}
            onBack={() => setStep(1)}
            onVerified={handleVerificationSuccess}
          />
        )}

        {step === 3 && (
          <CompleteProfileForm
            onBack={() => setStep(2)}
          />
        )}
      </div>
    </div>
  );
}