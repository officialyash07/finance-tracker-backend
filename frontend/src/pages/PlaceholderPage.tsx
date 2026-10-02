function PlaceholderPage({ title }: { title: string }) {
    return (
        <div className="flex min-h-screen items-center justify-center bg-canvas">
            <h1 className="text-2xl font-semibold text-ink-900">{title}</h1>
        </div>
    );
}

export default PlaceholderPage;
